import { useEffect, useRef } from "react";
import Gantt from "frappe-gantt";
import { HashIcon } from "lucide-react";
import DashboardCards from "./DashboardCards";

export default function Dashboard() {
    const ganttRef = useRef<HTMLDivElement | null>(null);
    useEffect(() => {
        const ganttEl = ganttRef.current;
        if (!ganttEl) return;

        ganttEl.innerHTML = "";

        const tasks = [
            {
                id: "Task 1",
                name: "Create wireframes",
                start: "2025-08-01",
                end: "2025-08-10",
                progress: 45,
            },
            {
                id: "Task 2",
                name: "Build frontend",
                start: "2025-08-02",
                end: "2025-08-09",
                progress: 20,
            },
            {
                id: "Task 3",
                name: "Build backend",
                start: "2025-08-02",
                end: "2025-08-09",
                progress: 75,
            },
        ];

        new Gantt(ganttEl, tasks, {
            view_mode: "Day",
            custom_popup_html: null,
        });
    }, []);

    return (
        <div className="w-full h-full px-3 flex flex-col space-y-3">
            <div className="sticky top-0 z-30 bg-customDarkBlueBG text-white pt-2 pb-3">
                <div className="w-full flex flex-row items-end pt-10">
                    <HashIcon className="h-[60%] w-[7%] text-white/60" />
                    <div className="flex items-end h-full w-full">
                        <p className="text-3xl pt-1 font-semibold">Dashboard 📊</p>
                    </div>
                </div>
                <p className="text-sm text-white/70 mt-1 ml-3">
                    This is where the team lead’s set deadline will be announced
                </p>
            </div>
            <div className="flex-1 overflow-y-auto pr-2 space-y">
                <div className="w-11/12 overflow-x-hidden bg-[#292b3c] 
                rounded-xl shadow-lg text-white mx-auto mt-2">
                    <div ref={ganttRef} className="gantt-target overflow-auto p-3" />
                </div>
                <DashboardCards />
            </div>

        </div>

    );
}
