interface UserModalProps {
  user: any;
  onClose: () => void;
  onLogout: () => void;
}

export default function UserModal({ user, onClose, onLogout }: UserModalProps) {
    return (
        <div className="fixed inset-0 bg-black/50 z-50 flex items-center justify-center">
            <div className="bg-primaryVividOrange w-[90%] max-w-md rounded-xl p-6 shadow-lg text-black font-montserrat">

                <div className="flex flex-col items-center gap-2">
                    <div
                        className="w-24 h-24 rounded-full bg-cover bg-center"
                        style={{ backgroundImage: `url(${user.avatarUrl || '/pattern.png'})` }}
                    />
                    <p><strong>Username:</strong> {user.username}</p>
                    <p><strong>Email:</strong> {user.email}</p>
                    <p><strong>Role:</strong> {user.role}</p>
                    <p><strong>Team ID:</strong> {user.teamId}</p>
                </div>

                <div className="mt-6 flex justify-between">
                    <button
                        onClick={onLogout}
                        className="bg-primaryStrongOrange hover:bg-white hover:text-primaryStrongOrange text-white px-4 py-2 rounded-lg"
                    >
                        Log Out
                    </button>
                    <button
                        onClick={onClose}
                        className="bg-gray-300 hover:bg-gray-400 text-black px-4 py-2 rounded-lg"
                    >
                        Close
                    </button>
                </div>
            </div>
        </div>
    );

}