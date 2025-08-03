"use client";

import React, { useState } from "react";
import { Task } from "../types";

type TaskListProps = {
  tasks: Task[];
  title: string;
  priority?: boolean;
  description?: string;
};

export default function TaskList({ tasks, title, priority }: TaskListProps) {
  const [selectedTask, setSelectedTask] = useState<Task | null>(null);

  return (
    <section className="w-[44%] bg-[#1e202d] p-4 border-r border-gray-800 flex flex-col h-screen">
      <div className="sticky top-0 bg-[#292b3c] rounded-md shadow p-4 z-10 flex justify-between items-center mb-4 overflow-y-auto">
        <h2 className="text-lg font-bold text-white">{title}</h2>
      </div>
      <div className="flex-1 min-h-0 overflow-y-auto">
        <div className="space-y-4">
          {tasks.map(task => (
            <div
              key={task.id}
              className="bg-[#23243a] rounded-lg shadow p-4 cursor-pointer"
              onClick={() => setSelectedTask(task)}
            >
              <div className="flex justify-between items-center">
                <span className={priority ? "text-yellow-400 font-semibold" : "text-gray-200 font-medium"}>
                  {task.taskName}
                </span>
                <span className="text-xs text-gray-400 ml-2">{task.status}</span>
              </div>
              <div className="text-sm text-gray-400">
                Due: {new Date(task.dueDate).toLocaleDateString()}
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Task Modal */}
      {selectedTask && (
        <div className="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50">
          <div className="bg-[#292b3c] rounded-2xl p-6 w-full max-w-md mx-auto shadow-2xl">
            <div className="flex flex-col items-center">
              <h3 className="text-2xl font-bold text-white mb-2">{selectedTask.taskName}</h3>
              <div className="text-gray-400 mb-2">{selectedTask.taskDescription}</div>
              <div className="mb-2 text-white">
                <span className="font-semibold">Status:</span> {selectedTask.status}
              </div>
              <div className="mb-2 text-white">
                <span className="font-semibold">Due Date:</span> {new Date(selectedTask.dueDate).toLocaleString()}
              </div>
              <div className="mb-2 text-white">
                <span className="font-semibold">Assigned To:</span> {selectedTask.userName}
              </div>
              <button
                className="mt-3 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                onClick={() => setSelectedTask(null)}
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </section>
  );
}