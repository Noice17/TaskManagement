"use client";

import React, { useState } from "react";
import { User, Team } from "../types";

type UserProfileProps = {
  user: User;
  onClose: () => void;
  teams: Team[];
  onUserUpdate: (userId: number, teamId: number | null) => void;
};

export default function UserProfile({ user, onClose, teams, onUserUpdate }: UserProfileProps) {
  const [edit, setEdit] = useState(false);
  const [selectedTeamId, setSelectedTeamId] = useState<number | "">(user.teamId ?? "");

  const handleSave = () => {
    onUserUpdate(user.id, selectedTeamId === "" ? null : Number(selectedTeamId));
    setEdit(false);
  };

  return (
    <section className="flex-1 bg-[#23243a] flex items-center justify-center h-screen">
      <div className="bg-[#292b3c] rounded-xl shadow-2xl p-10 w-[350px] max-w-md mx-auto text-center flex flex-col items-center">
        <img src={user.avatarUrl} alt={user.username} className="w-24 h-24 rounded-full object-cover mb-6 border-4 border-gray-700" />
        <h2 className="text-2xl font-bold text-white mb-2">{user.username}</h2>
        <div className="mb-1 text-gray-400">{user.role}</div>
        <div className="mb-1 text-gray-400">{user.email}</div>
        <div className="mb-6 text-green-400">
          <span className="font-semibold text-white">Team:</span> {user.teamName ?? "None"}
        </div>
        {edit ? (
          <>
            <select
              value={selectedTeamId}
              onChange={e => setSelectedTeamId(e.target.value === "" ? "" : Number(e.target.value))}
              className="mb-4 px-4 py-2 rounded bg-[#23243a] text-white border border-gray-700"
            >
              <option value="">No Team</option>
              {teams.map(team => (
                <option value={team.id} key={team.id}>{team.name}</option>
              ))}
            </select>
            <button onClick={handleSave} className="bg-blue-600 text-white rounded px-5 py-2 font-bold hover:bg-blue-700 transition mb-2">
              Save
            </button>
            <button onClick={() => setEdit(false)} className="text-gray-300 hover:text-gray-100 mb-2">Cancel</button>
          </>
        ) : (
          <button onClick={() => setEdit(true)} className="px-5 py-2 bg-yellow-500 text-white rounded shadow hover:bg-yellow-600 transition mb-2">
            Edit
          </button>
        )}
        <button onClick={onClose} className="px-5 py-2 bg-blue-600 text-white rounded shadow hover:bg-blue-700 transition">
          Go Back
        </button>
      </div>
    </section>
  );
}