export default function TaskTracker() {
    return (
        <div className="text-white bg-customDarkBlueBG w-full h-full
        flex flex-row space-x-3 p-5 font-montserrat">
            <div className="h-full flex-1 bg-nuanceDarkPastelBlue
            flex flex-col">
                <p className="w-full text-center bg-primaryStrongOrange py-3">NEWLY ADDED</p>
                <div className="flex-1 overflow-y-auto">

                </div>
            </div>
            <div className="h-full flex-1 bg-nuanceDarkPastelBlue
            flex flex-col">
                <p className="w-full text-center bg-primaryVividOrange py-3">STARTED</p>
                <div className="flex-1 overflow-y-auto">

                </div>
            </div>
            <div className="h-full flex-1 bg-nuanceDarkPastelBlue
            flex flex-col">
                <p className="w-full text-center bg-highlightGreen py-3">FINISHED</p>
                <div className="flex-1 overflow-y-auto">

                </div>
            </div>
        </div>
    )
}