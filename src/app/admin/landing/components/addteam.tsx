"use client";

import React, { useState } from "react";
import { Team } from "../types";

type Props = {
  open: boolean;
  onClose: () => void;
  onTeamAdded: (team: Team) => void;
};

export default function AddTeamModal({ open, onClose, onTeamAdded }: Props) {
  const [name, setName] = useState("");
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [loading, setLoading] = useState(false);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setImageFile(e.target.files[0]);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      const token = localStorage.getItem("token");
      const formData = new FormData();
      formData.append(
        "team",
        new Blob([JSON.stringify({ name })], { type: "application/json" })
      );
      if (imageFile) formData.append("image", imageFile);

      const response = await fetch("https://chic-integrity-production.up.railway.app/api/teams", {
        method: "POST",
        headers: { Authorization: `Bearer ${token}` },
        body: formData,
      });

      if (!response.ok) throw new Error("Failed to add team");
      const newTeam = await response.json();
      onTeamAdded(newTeam);
      setName("");
      setImageFile(null);
      onClose();
    } catch (err) {
      alert("Failed to add team.");
    } finally {
      setLoading(false);
    }
  };

  if (!open) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50">
      <form
        className="bg-[#292b3c] rounded-2xl p-6 w-full max-w-sm mx-auto shadow-2xl flex flex-col gap-2"
        onSubmit={handleSubmit}
      >
        <h3 className="text-2xl font-bold text-white mb-4">Add Team</h3>
        <input
          type="text"
          placeholder="Team Name"
          value={name}
          onChange={e => setName(e.target.value)}
          required
          className="mb-2 px-3 py-2 rounded bg-[#23243a] text-white border border-gray-700"
        />
        <label
        htmlFor="team-image-upload"
        className="mb-2 px-2 py-1 rounded bg-blue-600 text-white font-medium cursor-pointer hover:bg-blue-700 transition block w-fit"
        style={{ fontSize: "0.95rem", maxWidth: "140px" }}
        >
        Choose Image
        <input
            id="team-image-upload"
            type="file"
            accept="image/*"
            onChange={handleImageChange}
            style={{ display: "none" }}
        />
        </label>
        {imageFile && (
        <img
            src={URL.createObjectURL(imageFile)}
            alt="Preview"
            className="mt-2 rounded w-10 h-10 object-cover border border-gray-700"
        />
        )}
        <div className="flex gap-2 mt-2">
          <button
            type="submit"
            disabled={loading}
            className="bg-green-600 text-white px-5 py-2 rounded font-bold"
          >
            {loading ? "Saving..." : "Save"}
          </button>
          <button
            type="button"
            className="bg-gray-600 text-white px-5 py-2 rounded font-bold"
            onClick={onClose}
            disabled={loading}
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}