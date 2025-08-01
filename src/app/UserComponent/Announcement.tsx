import { HashIcon } from 'lucide-react';
import AnnouncementItems from './AnnouncementItems';
import MessageSender from '../GlobalComponent/MessageSender';

export default function Announcement() {
  return (
    <div className="w-full h-full bg-customDarkBlueBG px-3 flex flex-col overflow-hidden">
      <div className="sticky top-0 z-30 bg-customDarkBlueBG text-white pt-2 pb-3">
        <div className="w-full flex flex-row items-end pt-10">
          <HashIcon className="h-[60%] w-[7%] text-white/60" />
          <div className="flex items-end h-full w-full">
            <p className="text-3xl pt-1 font-semibold">Deadlines⏰</p>
          </div>
        </div>
        <p className="text-sm text-white/70 mt-1 ml-3">
          This is where the team lead’s set deadline will be announced
        </p>
      </div>

      {/* Scrollable content */}
      <div className="flex-1 overflow-y-auto pr-2 space-y">
        <div className="sticky top-0 z-20 bg-customDarkBlueBG">
          <div className="mt-10">
            <MessageSender />
          </div>
        </div>
        <AnnouncementItems />
        <AnnouncementItems />
        <AnnouncementItems />
        <AnnouncementItems />
        <AnnouncementItems />
        <AnnouncementItems />
      </div>
    </div>
  );
}

