export default function DashboardCards(){
    return(
        <div className="w-full h-fit flex flex-row text-white font-montserrat
        mt-5 mb-5 px-3 space-x-5">
            <div className="bg-primaryVividOrange w-1/3 p-3 rounded-2xl">
                <p className="text-[16px] italic font-bold">UPCOMING DEADLINE</p>
                <p className="text-5xl font-bold">T. ID</p>
                <p className="text-sm">DATE HERE</p>
            </div>
            <div className="bg-nuanceDarkPastelBlue w-1/3 p-3 rounded-2xl">
                <p className="text-[16px] italic font-bold">ADDED DEADLINE</p>
                <p className="text-5xl font-bold">T. ID</p>
                <p className="text-sm">DATE HERE</p>
            </div>
            <div className="w-1/3 rounded-2xl flex flex-col space-y-2">
                <div className="bg-nuanceDarkPastelBlue flex-1 px-3 py-1">
                    <p className="text-sm italic font-semibold">PENDING DEADLINE</p>
                    <p className="text-center">10</p>
                </div>
                <div className="bg-nuanceDarkPastelBlue flex-1 px-3 py-1">
                    <p className="text-sm italic font-semibold">FINISHED DEADLINE</p>
                    <p className="text-center">10</p>
                </div>
            </div>
        </div>
    )
}