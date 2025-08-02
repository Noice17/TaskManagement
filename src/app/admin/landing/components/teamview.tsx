"use client";

import React from "react";
import { Team, Task, User } from "../types";

type TeamViewProps = {
  team: Team;
  allTasks: Task[];
  users: User[];
  onClose: () => void;
};

export default function TeamView({ team, allTasks, users, onClose }: TeamViewProps) {
  const teamMembers = (users ?? []).filter(user => user.teamId === team.id);
  const teamUserIds = teamMembers.map(user => user.id);
  const teamTasks = allTasks.filter(task => teamUserIds.includes(task.userId));

  const sortedTasks = teamTasks
    .slice()
    .sort((a, b) => new Date(a.dueDate).getTime() - new Date(b.dueDate).getTime());

  return (
    <>
      <section className="w-[60%] bg-[#1e202d] p-8 flex flex-col h-screen overflow-y-auto">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-2xl font-bold text-white">{team.name} Tasks</h2>
          <button
            onClick={onClose}
            className="px-4 py-2 bg-blue-600 text-white rounded shadow hover:bg-blue-700 transition"
          >
            Go Back
          </button>
        </div>
        <div className="space-y-4">
          {sortedTasks.map((task, i) => (
            <div
              key={task.id}
              className={`bg-[#23243a] rounded-lg shadow p-4 border-l-4 ${
                i === 0 ? "border-yellow-400" : "border-gray-700"
              }`}
            >
              <span
                className={`font-semibold ${
                  task.status === "PASSED_DEADLINE" || i === 0
                    ? "text-yellow-400"
                    : "text-gray-200"
                }`}
              >
                {task.taskName}
              </span>
              {(task.status === "PASSED_DEADLINE" || i === 0) && (
                <span className="ml-2 text-xs bg-yellow-400 text-black px-2 py-1 rounded">
                  {task.status === "PASSED_DEADLINE" ? "Passed Deadline" : "Nearest Due"}
                </span>
              )}
              <div className="text-sm text-gray-400 mt-2">
                Status: <span className="font-semibold">{task.status}</span>
              </div>
              <div className="text-sm text-gray-400">
                Due: {new Date(task.dueDate).toLocaleString()}
              </div>
              <div className="text-sm text-gray-400">
                Assigned to: <span className="font-semibold">{task.userName}</span>
              </div>
            </div>
          ))}
        </div>
      </section>
      <section className="w-[40%] bg-[#292b3c] p-8 flex flex-col h-screen overflow-y-auto">
        <h2 className="text-2xl font-bold text-white mb-6">{team.name} Members</h2>
        <ul className="space-y-6">
          {teamMembers.length === 0 ? (
            <li className="text-gray-400">No members in this team.</li>
          ) : (
            teamMembers.map(user => (
              <li key={user.email} className="flex items-center gap-4 bg-[#23243a] p-4 rounded-lg shadow">
                <img
                  src={user.avatarUrl}
                  alt={user.username}
                  className="w-12 h-12 rounded-full object-cover border border-gray-700"
                />
                <div>
                  <div className="text-white font-semibold">{user.username}</div>
                  <div className="text-gray-400 text-sm">{user.role}</div>
                  <div className="text-gray-400 text-xs">{user.email}</div>
                </div>
              </li>
            ))
          )}
        </ul>
      </section>
    </>
  );
}