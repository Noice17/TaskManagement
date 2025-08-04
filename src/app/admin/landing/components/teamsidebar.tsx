"use client";

import React from "react";
import { Team } from "../types";

type TeamSidebarProps = {
  teams: Team[];
  onSelect: (team: Team) => void;
  onAddTeam: () => void;
};

export default function TeamSidebar({ teams, onSelect, onAddTeam }: TeamSidebarProps) {
  return (
    <aside className="w-full bg-[#292b3c] p-4 flex flex-col gap-6 border-r border-gray-800 h-screen">
      <h2 className="sticky top-0 bg-[#292b3c] z-10 text-lg font-bold text-white mb-4">Teams</h2>
      <div className="flex-1 min-h-0 overflow-y-auto">
        <ul className="flex flex-col gap-6">
          {teams.map(team => (
            <li
              key={team.name}
              className="group flex flex-col items-center cursor-pointer"
              onClick={() => onSelect(team)}
            >
              <img
                src={team.imageUrl || "/pattern.png"}
                alt={team.name}
                className="w-12 h-12 rounded-full object-cover border-2 border-gray-700 transition duration-200"
              />
              <span className="mt-2 text-gray-200 text-sm opacity-0 group-hover:opacity-100 group-hover:translate-y-1 transition duration-200 text-center">
                {team.name}
              </span>
            </li>
          ))}
          <li className="flex flex-col items-center cursor-pointer group" onClick={onAddTeam}>
            <div className="w-12 h-12 rounded-full bg-green-600 flex items-center justify-center text-white text-2xl font-bold border-2 border-gray-700 hover:bg-green-700 transition">
              +
            </div>
            <span className="mt-2 text-white-400 text-sm opacity-100 text-center font-semibold group-hover:text-green-300 transition duration-200">
              Add Team
            </span>
          </li>
        </ul>
      </div>
    </aside>
  );
}