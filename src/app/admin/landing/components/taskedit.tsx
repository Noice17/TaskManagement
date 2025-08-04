"use client";

import React, { useState, useEffect } from "react";
import { Task, User } from "../types";
import axios from "axios";

type Props = {
  open: boolean;
  onClose: () => void;
  task?: Task;
  users: User[];
  onSaved: (task: Task) => void;
};

export default function TaskEditModal({ open, onClose, task, users, onSaved }: Props) {
  const [taskName, setTaskName] = useState(task?.taskName ?? "");
  const [taskDescription, setTaskDescription] = useState(task?.taskDescription ?? "");
  const [dueDate, setDueDate] = useState(task?.dueDate ?? "");
  const taskType = "TEAM";
  const [userId, setUserId] = useState<number>(
    task?.userId ?? (users.length > 0 ? users[0].id : 0)
  );
  const [status, setStatus] = useState(task?.status ?? "ADDED");

  useEffect(() => {
    if (task) {
      setTaskName(task.taskName);
      setTaskDescription(task.taskDescription);
      setDueDate(task.dueDate ?? "");
      setUserId(task.userId);
      setStatus(task.status);
    } else {
      setTaskName("");
      setTaskDescription("");
      setDueDate("");
      setUserId(users.length > 0 ? users[0].id : 0);
      setStatus("ADDED");
    }
  }, [task, users]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    try {
      const payload = {
        taskName,
        taskDescription,
        dueDate,
        userId,
        status,
        taskType
      };

      let response;
      if (task) {
        response = await axios.put(
          `https://chic-integrity-production.up.railway.app/api/tasks/${task.id}`,
          payload,
          { headers: { Authorization: `Bearer ${token}` } }
        );
      } else {
        response = await axios.post(
          `https://chic-integrity-production.up.railway.app/api/tasks`,
          payload,
          { headers: { Authorization: `Bearer ${token}` } }
        );
      }

      onSaved(response.data);
      onClose();
    } catch (err) {
      alert("Failed to save task.");
    }
  };

  if (!open) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50">
      <form className="bg-[#292b3c] rounded-2xl p-6 w-full max-w-md mx-auto shadow-2xl flex flex-col gap-2"
            onSubmit={handleSubmit}>
        <h3 className="text-2xl font-bold text-white mb-2">{task ? "Edit Task" : "Add Task"}</h3>
        <input
          type="text"
          placeholder="Task Name"
          value={taskName}
          onChange={e => setTaskName(e.target.value)}
          required
          className="mb-2 px-3 py-2 rounded bg-[#23243a] text-white border border-gray-700"
        />
        <textarea
          placeholder="Task Description"
          value={taskDescription}
          onChange={e => setTaskDescription(e.target.value)}
          required
          className="mb-2 px-3 py-2 rounded bg-[#23243a] text-white border border-gray-700"
        />
        <input
          type="datetime-local"
          placeholder="Due Date"
          value={dueDate}
          onChange={e => setDueDate(e.target.value)}
          required
          className="mb-2 px-3 py-2 rounded bg-[#23243a] text-white border border-gray-700"
        />
        <select
          value={userId}
          onChange={e => setUserId(Number(e.target.value))}
          required
          className="mb-2 px-3 py-2 rounded bg-[#23243a] text-white border border-gray-700"
          disabled={users.length === 0}
        >
          {users.length === 0 ? (
            <option value={0}>No users available</option>
          ) : (
            users.map(u => (
              <option key={u.id} value={u.id}>{u.username}</option>
            ))
          )}
        </select>
        <select
          value={status}
          onChange={e => setStatus(e.target.value as any)}
          required
          className="mb-2 px-3 py-2 rounded bg-[#23243a] text-white border border-gray-700"
        >
          <option value="ADDED">Added</option>
          <option value="PENDING">Pending</option>
          <option value="COMPLETED">Completed</option>
        </select>
        <div className="flex gap-2 mt-2">
          <button type="submit" className="bg-blue-600 text-white px-5 py-2 rounded font-bold">
            Save
          </button>
          <button type="button" className="bg-gray-600 text-white px-5 py-2 rounded font-bold" onClick={onClose}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}