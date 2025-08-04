'use client';

import { useState } from "react";
import ContentControl from "../GlobalComponent/ContentControl";
import Announcement from "./Announcement";
import Dashboard from "./Dashboard";
import Tasks from "./Tasks";
import { User } from "./page";

interface Props {
    admin: User | null;
    user: User;
}


export default function LandingComponent({ admin, user }: Props) {
    const [currentView, setCurrentView] = useState("announcement");

    const renderView = () => {
        switch (currentView) {
            case "dashboard":
                return <Dashboard admin={admin} />;
            case "tasklist":
                return <Tasks user={user} />
            default:
                return <Announcement admin={admin} />;
        }
    };

    return (
        <div className="w-full h-full bg-customDarkBlueBG flex flex-row">
            <div className="w-1/4 h-full border-x border-nuanceDarkPastelBlue bg-red text-white">
                <ContentControl onChangeView={setCurrentView} user={user} />
            </div>

            <div className="w-3/4 h-full overflow-y-auto">
                {renderView()}
            </div>
        </div>
    )
}