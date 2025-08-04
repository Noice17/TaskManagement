interface AvatarModalProps {
  onSelect: (url: string, file?: File) => void
}

export default function AvatarModal({ onSelect }: AvatarModalProps){
    return(
        <div className="fixed inset-0 z-50 bg-customDarkBlueBG/60 bg-opacity-50 flex items-center justify-center">
          <div className="bg-white p-6 rounded-lg shadow-lg max-w-md w-full space-y-4">
            <h2 className="text-xl font-semibold text-gray-800">Choose Your Avatar</h2>
            <div className="grid grid-cols-3 gap-4">
              {[
                "/Bulbasaur_Icon.png",
                "/Charmander_Icon.png",
                "/Squirtle_Icon.png",
                "/Meloetta_Icon.png",
                "/Jirachi_Icon.png",
                "/Celebi_Icon.png",
                
              ].map((url, index) => (
                <img
                  key={index}
                  src={url}
                  alt={`avatar-${index}`}
                  className="w-fit h-20 object-cover cursor-pointer rounded-full shadow-md
                  hover:ring-2 hover:ring-blue-500"
                  onClick={() => onSelect(url)}
                />
              ))}
            </div>
          </div>
        </div>
    );
}