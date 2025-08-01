export default function MessageSender(){
    return(
        <div className="py-1">
            <div className="flex flex-row items-center text-white mb-1">
                <div className="h-[50px] w-[50px] border border-white rounded-full mt-3"></div>
                <div className="h-full flex flex-col">
                    <div className="flex flex-row font-semibold items-end">
                        <p className="text-white/70 px-2 pt-2 text-xl">Jai Layos</p>
                        <p className="text-white/50 pt-2 pb-1 text-sm">DATE HERE</p>
                    </div>
                    <p className="text-white/70 font-light px-2 text-sm">Position here</p>
                </div>
                
            </div>
        </div>
    )
}