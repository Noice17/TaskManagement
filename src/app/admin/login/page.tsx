"use client";

import { useState } from "react";
import axios from "axios";
import { useRouter } from "next/navigation";
import Link from "next/link";

export default function AdminLogin() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    try {
      const res = await axios.post("https://chic-integrity-production.up.railway.app/api/auth/login", {
        email,
        password
      });
      localStorage.setItem("token", res.data.token);
      router.push("/admin/landing");
    } catch (err: any) {
      setError("Invalid credentials. Please try again.");
    }
  };

  return (
    <main className="min-h-screen flex items-center justify-center bg-[#292b3c]">
      <form
        onSubmit={handleSubmit}
        className="bg-[#151727] p-8 rounded-lg shadow-md w-full max-w-sm"
      >
        <h1 className="text-3xl font-bold mb-10 text-[#f8f8f8] text-center">Admin Login</h1>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={e => setEmail(e.target.value)}
          className="w-full p-2 mb-4 border rounded focus:outline-none"
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={e => setPassword(e.target.value)}
          className="w-full p-2 mb-4 border rounded focus:outline-none"
          required
        />
        {error && <div className="text-red-500 mb-4 text-center">{error}</div>}
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
        >
          Login
        </button>
      </form>
    </main>
  );
}