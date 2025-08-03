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
import { useCurrentUser } from "@/app/hooks/currentuser";
import axios from "axios";

export default function AdminLanding() {
  const [teams, setTeams] = useState<Team[]>([]);
  const [users, setUsers] = useState<User[]>([]);
  const [tasks, setTasks] = useState<Task[]>([]);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);
  const [selectedTeam, setSelectedTeam] = useState<Team | null>(null);

  const currentUser = useCurrentUser();

  useEffect(() => {
    fetchTeams().then(setTeams);
    fetchUsers().then(setUsers);
    fetchTasks().then(setTasks);
  }, []);

  // Handler to update user's team
  const handleUserUpdate = async (userId: number, teamId: number | null) => {
    const token = localStorage.getItem("token");
    try {
      await axios.put(
        `https://chic-integrity-production.up.railway.app/api/users/${userId}`,
        { teamId }, // Backend expects { teamId }
        { headers: { Authorization: `Bearer ${token}` } }
      );
      // Update users state locally
      setUsers(prev =>
        prev.map(u =>
          u.id === userId
            ? { ...u, teamId, teamName: teams.find(t => t.id === teamId)?.name ?? null }
            : u
        )
      );
      // Update selected user if open
      if (selectedUser && selectedUser.id === userId) {
        setSelectedUser(prev =>
          prev
            ? { ...prev, teamId, teamName: teams.find(t => t.id === teamId)?.name ?? null }
            : null
        );
      }
    } catch (err) {
      alert("Failed to update user team.");
    }
  };

  const PRIORITY_TASKS = tasks.filter(t => t.status === "PASSED_DEADLINE" || t.status === "ASSIGNED");

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
          <TeamView team={selectedTeam} allTasks={tasks} users={users} onClose={() => setSelectedTeam(null)} />
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
            <UserProfile
              user={selectedUser}
              onClose={() => setSelectedUser(null)}
              teams={teams}
              onUserUpdate={handleUserUpdate}
            />
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