import { User } from "../UserComponent/page";

interface AdminSideButtonProps {
  admin: User | null;
}

export default function AdminSideButton({ admin }: AdminSideButtonProps) {
    return (
        <div className="w-full h-1/5 bg-primaryStrongOrange rounded-lg cursor-pointer">
            <div className="h-full flex flex-row items-center text-white mb-1 p-1">
                <div className="h-[90%] w-[20%] rounded-full bg-cover bg-center bg-no-repeat"
                style={{ backgroundImage: `url(${admin?.avatarUrl || '/pattern.png'})` }}/>
                <div className="h-full flex flex-col justify-center leading-tight">
                    <div className="flex font-semibold items-end leading-none">
                        <p className="text-white/70 px-2 text-sm leading-tight">{admin?.username}</p>
                    </div>
                    <div className="flex font-light items-start leading-none mt-[-2px]">
                        <p className="text-white/50 px-2 text-xs leading-tight">Team Leader</p>
                    </div>
                </div>
            </div>
        </div>
    );
}
