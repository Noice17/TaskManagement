"use client";

import Link from "next/link";

export default function Home() {
  return (
    <main className="custom-bg flex flex-col items-center justify-center min-h-screen bg-gray-50">
      <img
        src="/distracklogo.png"
        alt="DisTrack Logo"
        className="w-24 h-24 mb-4"
      />
      <h1 className="text-4xl font-bold text-gray-800 mb-8">DisTrack</h1>
      <div className="flex gap-6">
        <Link href="/user/login">
          <button className="px-6 py-3 bg-[#f8864f] text-white rounded-md shadow hover:bg-blue-700 transition">
            I'm a user
          </button>
        </Link>
        <Link href="/admin/login">
          <button className="px-6 py-3 bg-[#34de8f] text-white rounded-md shadow hover:bg-green-700 transition">
            I'm an admin
          </button>
        </Link>
      </div>
    </main>
  );
}