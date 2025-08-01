"use client";

import React from "react";
import { User } from "../types";

type UserProfileProps = {
  user: User;
  onClose: () => void;
};

export default function UserProfile({ user, onClose }: UserProfileProps) {
  return (
    <section className="flex-1 bg-[#23243a] flex items-center justify-center h-screen">
      <div className="bg-[#292b3c] rounded-xl shadow-2xl p-10 w-[350px] max-w-md mx-auto text-center flex flex-col items-center">
        <img
          src={user.avatarUrl}
          alt={user.username}
          className="w-24 h-24 rounded-full object-cover mb-6 border-4 border-gray-700"
        />
        <h2 className="text-2xl font-bold text-white mb-2">{user.username}</h2>
        <div className="mb-1 text-gray-400">{user.role}</div>
        <div className="mb-6 text-gray-400">{user.email}</div>
        <button
          onClick={onClose}
          className="px-5 py-2 bg-blue-600 text-white rounded shadow hover:bg-blue-700 transition"
        >
          Go Back
        </button>
      </div>
    </section>
  );
}