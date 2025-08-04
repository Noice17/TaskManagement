export default function TeamAssignmentModal({ onAcknowledge }: { onAcknowledge: () => void }) {
  return (
    <div className="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50">
      <div className="bg-white p-8 rounded-xl shadow-xl text-center space-y-4 max-w-md">
        <h2 className="text-xl font-semibold text-gray-800">Team Assignment Pending</h2>
        <p className="text-gray-600">
          You haven't been assigned to a team yet. Please wait for your leader to assign you before logging in.
        </p>
        <button
          onClick={onAcknowledge}
          className="mt-4 px-4 py-2 bg-primaryStrongOrange text-white rounded hover:bg-primaryVividOrange transition"
        >
          Acknowledge & Log Out
        </button>
      </div>
    </div>
  );
}
