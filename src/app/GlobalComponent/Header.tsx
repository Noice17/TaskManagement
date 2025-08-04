"use client";

import { useState } from "react";
import UserModal from "./UserModal";

interface HeaderProps {
    user: any;
}

export default function Header({ user }: HeaderProps) {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleLogout = () => {
        localStorage.removeItem("token");
        window.location.href = "/"; // or router.push("/")
    };
    return (
        <>
            <div className="w-full h-1/4 bg-customDarkBlueBG text-white font-montserrat font-extrabold
         flex flex-row justify-end pl-3 pb-3 border-b border-nuanceDarkPastelBlue">
                <div className="flex-1 flex flex-col h-full justify-end">
                    <p className="text-5xl font-bold">DisTrack</p>
                    <div className="flex flex-row items-center">
                        <div
                            className="h-[50px] w-[50px] rounded-full bg-cover bg-center"
                            style={{ backgroundImage: `url(${user.avatarUrl || '/pattern.png'})` }}
                        />
                        <p className="text-white/70 font-light px-2 pt-2">{user?.username || "Unauthenticated"}</p>
                    </div>
                </div>
                <div className=" w-1/4 flex items-end justify-center pb-1">
                    <div className="w-[40%] h-[70%] rounded-2xl flex bg-gradient-to-br 
                from-primaryVividOrange to-primaryStrongOrange"
                        onClick={() => setIsModalOpen(true)}>
                        <div
                            className="h-[60%] w-[60%] rounded-full my-auto mx-auto bg-cover bg-center bg-no-repeat"
                            style={{
                                backgroundImage: `url(${user.avatarUrl || '/pattern.png'})`,
                            }}
                        />
                    </div>

                </div>
            </div>
            {isModalOpen && (
                <UserModal
                    user={user}
                    onClose={() => setIsModalOpen(false)}
                    onLogout={handleLogout}
                />)}
        </>


    )
}