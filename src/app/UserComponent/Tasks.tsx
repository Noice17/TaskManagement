'use client';

import { useEffect, useState } from "react";
import { HashIcon, SendIcon } from "lucide-react";
import { User } from "./page";
import { fetchPersonalTasks, createPersonalTask } from "../api";

interface Task {
  id: number;
  taskName: string; // updated
  createdAt: string;
}

interface TasksProps {
  user: User;
}

export default function Tasks({ user }: TasksProps) {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [newTask, setNewTask] = useState("");

  // Fetch personal tasks on mount
  useEffect(() => {
    console.log("Fetching tasks for user ID:", user.id); // 👈 print userId
    const loadTasks = async () => {
      try {
        const personalTasks = await fetchPersonalTasks(user.id);
        setTasks(personalTasks);
      } catch (error) {
        console.error("Failed to fetch personal tasks:", error);
      }
    };
    loadTasks();
  }, [user.id]);

  // Handle send button click
  const handleSend = async () => {
    if (!newTask.trim()) return;
    try {
      const created = await createPersonalTask(user.id, newTask); // task name instead of description
      setTasks((prev) => [...prev, created]);
      setNewTask("");
    } catch (error) {
      console.error("Failed to create personal task:", error);
    }
  };

  return (
    <div className="w-full h-screen bg-customDarkBlueBG flex flex-col">
      {/* Header */}
      <div className="sticky top-0 z-30 bg-customDarkBlueBG text-white pt-2 pb-3 px-3">
        <div className="w-full flex flex-row items-end pt-10">
          <HashIcon className="h-[60%] w-[7%] text-white/60" />
          <div className="flex items-end h-full w-full">
            <p className="text-3xl pt-1 font-semibold">Task List📋</p>
          </div>
        </div>
        <p className="text-sm text-white/70 mt-1 ml-3">
          This is where I list my personal tasks for today.
        </p>
      </div>

      <div className="flex-1 overflow-y-auto space-y-2 px-3 py-4">
        {tasks.map((task) => (
          <div
            key={task.id}
            className="text-white px-4 py-2 rounded-md"
          >
            <p className="text-sm">{task.taskName}</p>
            <p className="text-xs text-gray-400">
              {new Date(task.createdAt).toDateString()}
            </p>
          </div>
        ))}
      </div>

      {/* Sticky Input */}
      <div className="sticky bottom-0 bg-customDarkBlueBG p-3">
        <div className="relative w-full">
          <input
            type="text"
            value={newTask}
            onChange={(e) => setNewTask(e.target.value)}
            placeholder="Type your task..."
            className="w-full p-2 pr-10 rounded-md bg-nuanceDarkPastelBlue/50 text-white 
            focus:outline-none focus:ring-2 focus:ring-[#34de8f]"
          />
          <button
            onClick={handleSend}
            className="absolute inset-y-0 right-2 flex items-center justify-center text-white hover:text-white"
          >
            <SendIcon className="w-5 h-5" />
          </button>
        </div>
      </div>
    </div>
  );
}
