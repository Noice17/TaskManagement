"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";
import { User } from "../types";

type Props = {
  user: User;
  onLogout: () => void;
  hideUsername?: boolean;
};

export default function UserPanel({ user, onLogout, hideUsername = false }: Props) {
  const [open, setOpen] = useState(false);
  const router = useRouter();

  const handleLogout = () => {
    onLogout();
    router.push("/");
  };
  
  return (
    <>
      <div
        className="mx-auto shadow-lg 
                rounded-xl left-20 
                w-full bg-gradient-to-r from-[#23243a] to-[#292b3c] 
                flex items-center justify-between 
                px-4 py-3 
                cursor-pointer 
                transition hover:bg-[#353657] z-50
                border border-gray-800"
        style={{ 
            width: "calc(100% - 32px)",
            marginLeft: "16px",
            marginRight: "16px",
            marginBottom: "16px"
         }}
        onClick={() => setOpen(true)}
      >
        <img
          src={user.avatarUrl}
          alt={user.username}
          className="w-8 h-8 rounded-full mr-3"
        />
        {!hideUsername && (
          <div className="flex-1">
            <div className="text-white font-semibold leading-tight">{user.username}</div>
          </div>
        )}
        <svg width={22} height={22} fill="none" stroke="white" viewBox="0 0 24 24">
          <path d="M12 15.5A3.5 3.5 0 1 0 12 8.5a3.5 3.5 0 0 0 0 7zm7-3.5a7 7 0 1 1-14 0 7 7 0 0 1 14 0z" />
        </svg>
      </div>

      {open && (
        <div className="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50">
          <div className="bg-[#292b3c] rounded-2xl p-6 w-full max-w-sm mx-auto shadow-2xl">
            <div className="flex flex-col items-center">
              <img
                src={user.avatarUrl}
                alt={user.username}
                className="w-16 h-16 rounded-full mb-2"
              />
              {!hideUsername && (
                <div className="text-white text-xl font-bold mb-1">{user.username}</div>
              )}
              {user.email && (
                <div className="text-gray-400 mb-4">{user.email}</div>
              )}
              <button
                onClick={handleLogout}
                className="bg-red-600 text-white rounded px-5 py-2 font-bold hover:bg-red-700 transition"
              >
                Log Out
              </button>
              <button
                className="mt-3 text-gray-300 hover:text-gray-100"
                onClick={() => setOpen(false)}
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}