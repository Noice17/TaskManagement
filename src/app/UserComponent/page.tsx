'use client';

import Logo from "../GlobalComponent/Logo";
import MenuIcon from "../GlobalComponent/MenuIcon";
import HomeIcon from "../GlobalComponent/HomeIcon";
import AdminSideButton from "../GlobalComponent/AdminSideButton";
import Header from "../GlobalComponent/Header";
import MeSideButton from "../GlobalComponent/MeSideButton";
import UserSideButton from "../GlobalComponent/UserSideButton";
import LandingComponent from "./LandingComponent";
import { useState, useEffect } from "react";
import TaskTracker from "./TaskTracker";
import { fetchCurrentUser, fetchAllUsers } from "../api";

export interface User {
  id: number;
  username: string;
  email: string;
  role: string;
  teamId: number;
}


export default function Home() {
  const [activePage, setActivePage] = useState<'landing' | 'task'>('landing');
  const [user, setUser] = useState(null);
  const [admin, setAdmin] = useState<User | null>(null);

  useEffect(() => {
    fetchCurrentUser()
      .then(async (data) => {
        setUser(data);
        console.log("Current user:", data); 
        try {
          const allUsers = await fetchAllUsers();

          const teamAdmin = allUsers.find((u: User) =>
            u.teamId === data.teamId && u.role === "ADMIN"
          );
          setAdmin(teamAdmin || null);
          console.log("User's Admin: ", teamAdmin)
        } catch (err) {
          console.error("Failed to fetch all users", err);
        }
      })
      .catch((err) => {
        console.error("Failed to fetch user", err);
      });
  }, []);


  if (!user) return <div>Loading...</div>;

  return (
    <div className="w-screen h-screen overflow-x-hidden">
      <div className="w-full h-full flex flex-row">
        <div className="w-1/6 bg-black flex flex-col">
          <div className="w-full h-1/4 border-b border-nuanceDarkPastelBlue">
            <Logo />
          </div>
          <div className="w-full h-3/4 flex flex-col">
            <div className="h-[40%] p-2 flex flex-col">
              <div className="h-1/3"
                onClick={() => setActivePage('landing')}>
                <HomeIcon />
              </div>
              <div className="h-1/3"
                onClick={() => setActivePage('task')}>
                <MenuIcon />
              </div>
            </div>
            <div className="h-[60%] p-2 flex flex-col space-y-2 overflow-y-auto">
              <AdminSideButton admin={admin}/>
              <MeSideButton user={user}/>
              <UserSideButton />
              <UserSideButton />
              <UserSideButton />
              <UserSideButton />
              <UserSideButton />
            </div>
          </div>
        </div>
        <div className="w-5/6 flex flex-col">
          <Header user={user} />
          <div className="flex-1 h-0 flex">
            {activePage === 'landing' ? <LandingComponent /> : <TaskTracker />}
          </div>
        </div>
      </div>
    </div>
  );
}
