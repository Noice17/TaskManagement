"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { registerUsers } from "@/app/api";

export default function UserRegister() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    try {
      await registerUsers(username, email, password);
      alert("User Registered!");
      router.push("/user/login"); 
    } catch (err: any) {
      setError(err.response?.data?.message || "Registration failed. Please try again.");
    }
  };

  return (
    <main className="min-h-screen flex items-center justify-center bg-[#23243a]">
      <form
        onSubmit={handleSubmit}
        className="bg-white/90 p-8 rounded-lg shadow-md w-full max-w-sm"
      >
        <h2 className="text-2xl font-bold mb-6 text-center text-[#292b3c]">Register</h2>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={e => setUsername(e.target.value)}
          className="w-full p-2 mb-4 border border-gray-400 rounded bg-white text-[#23243a] placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-400"
          required
        />
        <input
          type="email"
          placeholder="Your email"
          value={email}
          onChange={e => setEmail(e.target.value)}
          className="w-full p-2 mb-4 border border-gray-400 rounded bg-white text-[#23243a] placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-400"
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={e => setPassword(e.target.value)}
          className="w-full p-2 mb-4 border border-gray-400 rounded bg-white text-[#23243a] placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-400"
          required
        />
        {error && <div className="text-red-500 mb-4 text-center">{error}</div>}
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
        >
          Sign up
        </button>
        <div className="mt-4 text-sm text-center text-gray-700">
          Already have an account? <a href="/login" className="text-blue-600 underline">Log in</a>
        </div>
      </form>
    </main>
  );
}
