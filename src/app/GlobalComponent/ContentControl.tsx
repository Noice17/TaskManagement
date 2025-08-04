'use client';

import { useState } from 'react';
import { ChevronDownIcon } from '@heroicons/react/20/solid';
import { HashIcon } from 'lucide-react';

type Props = {
    onChangeView: (view: string) => void;
};

export default function ContentControl({ onChangeView }: Props) {
    const [openMenus, setOpenMenus] = useState<{ [key: string]: boolean }>({
        announcement: true,
        tools: true,
    });

    const toggleMenu = (menu: string) => {
        setOpenMenus(prev => ({ ...prev, [menu]: !prev[menu] }));
    };

    return (
        <div className="w-full h-full font-montserrat">
            <div className="w-full h-1/5 bg-cover bg-no-repeat" 
            style={{ backgroundImage: "url('/pattern.png')" }}/>
            <div className="w-full h-4/5 flex flex-col">
                <p className="font-bold text-lg text-white/60 border-y border-nuanceDarkPastelBlue py-1 px-3">TEAM NAME</p>

                <button
                    onClick={() => toggleMenu('announcement')}
                    className="text-white/60 px-4 py-2 flex items-center gap-2 w-full text-left"
                >
                    <ChevronDownIcon
                        className={`h-4 w-4 transition-transform duration-200 ${openMenus.announcement ? 'rotate-180' : ''
                            }`}
                    />
                    <span>Announcement</span>
                </button>
                {openMenus.announcement && (
                    <div className='w-full'>
                        <div className="text-white w-1/2 flex flex-row mx-auto text-sm cursor-pointer"
                        onClick={() => onChangeView("announcement")}>
                            <HashIcon className="h-4 w-4 text-white/60" />
                            <p>Deadlines⏰</p>
                        </div>
                    </div>

                )}

                {/* Tools Section */}
                <button
                    onClick={() => toggleMenu('tools')}
                    className="text-white/60 px-4 py-2 flex items-center gap-2 w-full text-left"
                >
                    <ChevronDownIcon
                        className={`h-4 w-4 transition-transform duration-200 ${openMenus.tools ? 'rotate-180' : ''
                            }`}
                    />
                    <span>Tools</span>
                </button>
                {openMenus.tools && (
                    <div className="text-white flex flex-col w-full text-sm">
                        <div className="text-white w-1/2 flex flex-row mx-auto cursor-pointer"
                        onClick={() => onChangeView("dashboard")}>
                            <HashIcon className="h-4 w-4 text-white/60" />
                            <p>Dashboard📊</p>
                        </div>
                        <div className="text-white w-1/2 flex flex-row mx-auto mt-1 cursor-pointer"
                        onClick={() => onChangeView("tasklist")}>
                            <HashIcon className="h-4 w-4 text-white/60" />
                            <p>Task List📋</p>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}
