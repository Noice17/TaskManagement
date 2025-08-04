'use client';

import { useEffect, useState } from "react";
import { fetchTaskById } from "../api";

interface Props {
  notification: {
    id: number;
    createdAt: string;
    description: string;
    read: boolean;
    taskId: number;
    teamId: number;
    userId: number;
    taskName?: string;
  };
}

export default function AnnouncementItems({ notification }: Props){
    console.log('Received notification:', notification); 
    const [taskName, setTaskName] = useState<string | null>(notification.taskName ?? null);

    useEffect(() => {
        if (!taskName) {
        const loadTaskName = async () => {
            try {
            const task = await fetchTaskById(notification.taskId);
            setTaskName(task.taskName); // 👈 or whatever the field is from your API
            } catch (error) {
            console.error("Failed to fetch task by ID:", error);
            }
        };
        loadTaskName();
        }
    }, [notification.taskId, taskName]);

    return(
        <div className="text-white font-montserrat h-fit w-3/5
        flex flex-row border-l-4 border-primaryVividOrange px-4
        bg-nuanceDarkPastelBlue/45 ml-3 py-5 my-5">
            <div>
                <p className="font-medium text-lg">Task #{notification.taskId}</p>
                <p className="font-bold text-xl">{taskName ?? "Loading..."}</p>
                <p className="font-light text-[16px]">
                    {notification.description}
                </p>
                <p className="font-light text-xs italic text-white/50">
                    {notification.createdAt}
                </p>
            </div>
        </div>
    )
} 