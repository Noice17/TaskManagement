import { User } from "../UserComponent/page" 

interface Props {
  admin: User | null;
}

export default function MessageSender({ admin }: Props){
    return(
        <div className="py-1">
            <div className="flex flex-row items-center text-white mb-1">
                <div className="h-[50px] w-[50px] rounded-full mt-3 bg-cover bg-center bg-no-repeat"
                style={{ backgroundImage: `url(${admin?.avatarUrl || '/pattern.png'})` }}/>
                <div className="h-full flex flex-col">
                    <div className="flex flex-row font-semibold items-end">
                        <p className="text-white/70 px-2 pt-2 text-xl">{admin?.username}</p>
                    </div>
                    <p className="text-white/70 font-light px-2 text-sm">Team Leader</p>
                </div>
                
            </div>
        </div>
    )
}