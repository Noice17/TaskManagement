import { useState } from "react";
import MemberModal from "./MemberModal"; // adjust path as needed

interface Props {
  user: any;
}

export default function UserSideButton({ user }: Props) {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  return (
    <>
      <div
        className="w-full h-1/5 bg-nuanceDarkPastelBlue rounded-lg cursor-pointer"
        onClick={() => setIsModalOpen(true)}
      >
        <div className="h-full flex flex-row items-center text-white mb-1 p-1">
          <div
            className="h-[90%] w-[20%] rounded-full bg-cover bg-center bg-no-repeat"
            style={{
              backgroundImage: `url(${user.avatarUrl || "/pattern.png"})`,
            }}
          />
          <div className="h-full flex flex-col justify-center leading-tight">
            <div className="flex font-semibold items-end leading-none">
              <p className="text-white/70 px-2 text-sm leading-tight">
                {user.username}
              </p>
            </div>
            <div className="flex font-light items-start leading-none mt-[-2px]">
              <p className="text-white/50 px-2 text-xs leading-tight">
                Team {user?.teamName || "TBA"}
              </p>
            </div>
          </div>
        </div>
      </div>

      {isModalOpen && (
        <MemberModal
          user={user}
          onClose={() => setIsModalOpen(false)}
          onLogout={handleLogout}
        />
      )}
    </>
  );
}
