import { HashIcon, SendIcon } from "lucide-react";

export default function Tasks() {
    return (
        <div className="w-full h-full bg-customDarkBlueBG px-3 flex flex-col overflow-hidden">
            <div className="sticky top-0 z-30 bg-customDarkBlueBG text-white pt-2 pb-3">
                <div className="w-full flex flex-row items-end pt-10">
                    <HashIcon className="h-[60%] w-[7%] text-white/60" />
                    <div className="flex items-end h-full w-full">
                        <p className="text-3xl pt-1 font-semibold">Task List📋</p>
                    </div>
                </div>
                <p className="text-sm text-white/70 mt-1 ml-3">
                    This is where I list my personal tasks for today.
                </p>
            </div>
            <div className="w-full flex-1 flex flex-col">
                <div className="flex-1">

                </div>
                <div className="w-full h-1/6 px-4 flex items-center">
                    <div className="relative w-full">
                        <input
                            type="text"
                            placeholder="Type here..."
                            className="w-full p-2 pr-10 rounded-md bg-nuanceDarkPastelBlue/50 text-white 
            focus:outline-none focus:ring-2 focus:ring-[#34de8f]"
                        />
                        <button
                            className="absolute inset-y-0 right-2 flex items-center justify-center text-white hover:text-white"
                        >
                            <SendIcon className="w-5 h-5" />
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}
