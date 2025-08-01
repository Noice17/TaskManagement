export default function UserSideButton() {
    return (
        <div className="w-full h-1/5 bg-nuanceDarkPastelBlue rounded-lg cursor-pointer">
            <div className="h-full flex flex-row items-center text-white mb-1 p-1">
                <div className="h-[90%] w-[20%] border border-white rounded-full"></div>
                <div className="h-full flex flex-col justify-center leading-tight">
                    <div className="flex font-semibold items-end leading-none">
                        <p className="text-white/70 px-2 text-lg leading-tight">Jai Layos</p>
                    </div>
                    <div className="flex font-light items-start leading-none mt-[-2px]">
                        <p className="text-white/50 px-2 text-sm leading-tight">Position here</p>
                    </div>
                </div>
            </div>
        </div>
    );
}