"use client";

import { useEffect, useState } from "react";
import TeamSidebar from "./components/teamsidebar";
import UserList from "./components/userlist";
import UserProfile from "./components/userprofile";
import TaskList from "./components/tasklist";
import TeamView from "./components/teamview";
import UserPanel from "./components/userpanel";
import { User, Task, Team } from "./types";
import { fetchTasks, fetchTeams, fetchUsers } from "@/app/api";

export default function AdminLanding() {
  const [teams, setTeams] = useState<Team[]>([]);
  const [users, setUsers] = useState<User[]>([]);
  const [tasks, setTasks] = useState<Task[]>([]);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);
  const [selectedTeam, setSelectedTeam] = useState<Team | null>(null);

  const currentUser: User = {
    id: 99,
    username: "timmy",
    email: "koralsus@example.com",
    role: "ADMIN",
    avatarUrl: "https://ui-avatars.com/api/?name=Timmy&background=292b3c&color=fff&size=128",
    createdAt: "2025-07-01T09:00:00Z",
    updatedAt: "2025-07-01T10:00:00Z"
  };

  useEffect(() => {
    fetchTeams().then(setTeams);
    fetchUsers().then(setUsers);
    fetchTasks().then(setTasks);
  }, []);

  const PRIORITY_TASKS = tasks.filter(
    t => t.status === "PASSED_DEADLINE" || t.status === "ASSIGNED"
  );

  return (
    <main className="flex min-h-screen bg-[#151727]">
      {selectedTeam ? (
        <>
          <div className="relative">
            <TeamSidebar teams={teams} onSelect={team => { setSelectedTeam(team); setSelectedUser(null); }} />
            <div className="absolute bottom-0 left-0 w-full">
              <UserPanel user={currentUser} onLogout={() => {}} hideUsername={!!selectedTeam} />
            </div>
          </div>
          <TeamView
            team={selectedTeam}
            allTasks={tasks}
            users={users}
            onClose={() => setSelectedTeam(null)}
          />
        </>
      ) : (
        <>
          <div className="relative flex">
            <TeamSidebar teams={teams} onSelect={team => { setSelectedTeam(team); setSelectedUser(null); }} />
            <UserList users={users} onSelect={setSelectedUser} />
            <div className="absolute bottom-0 left-0 w-full">
              <UserPanel user={currentUser} onLogout={() => {}} />
            </div>
          </div>

          {selectedUser ? (
            <section className="flex-1 flex bg-[#23243a] items-center justify-center h-screen">
              <UserProfile user={selectedUser} onClose={() => setSelectedUser(null)} />
            </section>
          ) : (
            <>
              <TaskList tasks={tasks} title="All Tasks" description="All tasks in the system." />
              <TaskList tasks={PRIORITY_TASKS} title="Priority Tasks" priority description="Tasks needing urgent attention." />
            </>
          )}
        </>
      )}
    </main>
  );
}