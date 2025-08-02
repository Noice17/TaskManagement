interface HeaderProps {
  user: any; 
}

export default function Header({ user }: HeaderProps) {
    return (
        <div className="w-full h-1/4 bg-customDarkBlueBG text-white font-montserrat font-extrabold
         flex flex-row justify-end pl-3 pb-3 border-b border-nuanceDarkPastelBlue">
            <div className="flex-1 flex flex-col h-full justify-end">
                <p className="text-5xl font-bold">DisTrack</p>
                <div className="flex flex-row items-center">
                    <div className="h-[50px] w-[50px] border border-white rounded-full"></div>
                    <p className="text-white/70 font-light px-2 pt-2">{user?.username || "Unauthenticated"}</p>
                </div>
            </div>
            <div className=" w-1/4 flex items-end">
                <div className="w-[50%] h-[90%] bg-nuanceDarkPastelBlue rounded-2xl flex">
                    <div className="h-[60%] w-[60%] border border-white rounded-full my-auto mx-auto"></div>
                </div>
            </div>
        </div>
    )
}