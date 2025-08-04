import { useEffect, useState } from "react";
import { fetchTasksByUserId, updateTaskStatus } from "../api";
import { User } from "./page";
import TaskModal from "./TaskModal";
import toast from "react-hot-toast";

interface Props {
    admin: User | null;
    user: User;
}

export interface Task {
    id: number;
    taskName: string;
    taskDescription: string;
    createdAt: string;
    dueDate: string;
    status: "ADDED" | "PENDING" | "COMPLETED";
    userId: number;
}

export default function TaskTracker({ admin, user }: Props) {
    const [newlyAdded, setNewlyAdded] = useState<Task[]>([]);
    const [started, setStarted] = useState<Task[]>([]);
    const [finished, setFinished] = useState<Task[]>([]);
    const [selectedTask, setSelectedTask] = useState<Task | null>(null);
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        const fetchTasks = async () => {
            if (!admin) return;
            try {
                const tasks: Task[] = await fetchTasksByUserId(admin.id);
                console.log("Tasks:", tasks);

                setNewlyAdded(tasks.filter(task => task.status === "ADDED"));
                setStarted(tasks.filter(task => task.status === "PENDING"));
                setFinished(tasks.filter(task => task.status === "COMPLETED"));
            } catch (error) {
                console.error("Failed to fetch tasks:", error);
            }
        };

        fetchTasks();
    }, [admin]);

    const handleTaskClick = (task: Task) => {
        console.log("Task ID to be updated:", task.id);
        if (task.status === "ADDED" || task.status === "PENDING") {
            setSelectedTask(task);
            setShowModal(true);
        }
    };

    const handleConfirmUpdateStatus = async () => {
        if (!selectedTask) return;

        const newStatus =
            selectedTask.status === "ADDED" ? "PENDING" :
                selectedTask.status === "PENDING" ? "COMPLETED" :
                    selectedTask.status;

        try {
            await updateTaskStatus(selectedTask.id, newStatus);

            if (newStatus === "PENDING") {
                setNewlyAdded(prev => prev.filter(t => t.id !== selectedTask.id));
                setStarted(prev => [...prev, { ...selectedTask, status: newStatus }]);
            } else if (newStatus === "COMPLETED") {
                setStarted(prev => prev.filter(t => t.id !== selectedTask.id));
                setFinished(prev => [...prev, { ...selectedTask, status: newStatus }]);
            }

            toast.success("Task status updated!");

            setShowModal(false);
            setSelectedTask(null);
        } catch (error) {
            console.error("Failed to update status:", error);
        }
    };

    const handleCancel = () => {
        setShowModal(false);
        setSelectedTask(null);
    };

    const renderTaskList = (tasks: Task[]) => (
        <ul className="p-4 space-y-2">
            {tasks.map((task) => (
                <li
                    key={task.id}
                    className="bg-white/10 p-3 rounded cursor-pointer hover:bg-white/20 transition"
                    onClick={() => handleTaskClick(task)}
                >
                    <p className="font-semibold">{task.taskName}</p>
                    <p className="text-xs italic">Created: {new Date(task.createdAt).toLocaleDateString()}</p>
                    {task.dueDate && (
                        <p className="text-xs italic">Deadline: {new Date(task.dueDate).toLocaleDateString()}</p>
                    )}
                </li>
            ))}
        </ul>
    );

    return (
        <>
            {/* Main Layout */}
            <div className="text-white bg-customDarkBlueBG w-full h-full flex flex-row space-x-3 p-5 font-montserrat">
                <div className="h-full flex-1 bg-nuanceDarkPastelBlue flex flex-col">
                    <p className="w-full text-center bg-primaryStrongOrange py-3">NEWLY ADDED</p>
                    <div className="flex-1 overflow-y-auto">{renderTaskList(newlyAdded)}</div>
                </div>
                <div className="h-full flex-1 bg-nuanceDarkPastelBlue flex flex-col">
                    <p className="w-full text-center bg-primaryVividOrange py-3">PENDING</p>
                    <div className="flex-1 overflow-y-auto">{renderTaskList(started)}</div>
                </div>
                <div className="h-full flex-1 bg-nuanceDarkPastelBlue flex flex-col">
                    <p className="w-full text-center bg-highlightGreen py-3">COMPLETED</p>
                    <div className="flex-1 overflow-y-auto">{renderTaskList(finished)}</div>
                </div>
            </div>

            {/* Confirmation Modal */}
            {showModal && selectedTask && (
                <TaskModal
                    selectedTask={selectedTask}
                    onCancel={handleCancel}
                    onConfirm={handleConfirmUpdateStatus}
                />
            )}


        </>
    );
}
