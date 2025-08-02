interface HeaderProps {
    user: any;
}

export default function MeSideButton({ user }: HeaderProps) {
    return (
        <div className="w-full h-1/5 bg-primaryVividOrange rounded-lg cursor-pointer">
            <div className="h-full flex flex-row items-center text-white mb-1 p-1">
                <div className="h-[90%] w-[20%] border border-white rounded-full"></div>
                <div className="h-full flex flex-col justify-center leading-tight">
                    <div className="flex font-semibold items-end leading-none">
                        <p className="text-white/70 px-2 text-sm leading-tight max-w-[120px] truncate">
                            {user?.username || "Guest"}
                        </p>                    </div>
                    <div className="flex font-light items-start leading-none mt-[-2px]">
                        <p className="text-white/50 px-2 text-xs leading-tight max-w-[140px] truncate">
                            Team {user?.teamName || "TBA"}
                        </p>                    </div>
                </div>
            </div>
        </div>
    );
}