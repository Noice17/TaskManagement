import { Task } from "./TaskTracker";

interface TaskModalProps {
    selectedTask: Task;
    onCancel: () => void;
    onConfirm: () => void;
}
export default function TaskModal({ selectedTask, onCancel, onConfirm }: TaskModalProps) {
    return (
        <div className="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
            <div className="bg-white text-black rounded-xl p-6 w-96">
                <h2 className="text-lg font-bold mb-4">
                    {selectedTask.status === "ADDED" ? "Start Task" : "Complete Task"}
                </h2>
                <p className="mb-4">
                    {selectedTask.status === "ADDED" && (
                        <>Have you started <span className="font-semibold">"{selectedTask.taskName}"</span>?</>
                    )}
                    {selectedTask.status === "PENDING" && (
                        <>Have you completed <span className="font-semibold">"{selectedTask.taskName}"</span>?</>
                    )}
                </p>
                <div className="flex justify-end space-x-2">
                    <button
                        className="bg-gray-300 px-4 py-2 rounded hover:bg-gray-400"
                        onClick={onCancel}
                    >
                        Cancel
                    </button>
                    <button
                        className={`${selectedTask.status === "ADDED"
                            ? "bg-primaryVividOrange"
                            : "bg-highlightGreen"
                            } text-white px-4 py-2 rounded hover:opacity-90`}
                        onClick={onConfirm}
                    >
                        Confirm
                    </button>
                </div>
            </div>
        </div>
    );
}