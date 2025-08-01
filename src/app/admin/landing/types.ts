export type User = {
  id: number;
  username: string;
  email: string;
  role: UserRole;
  teamId?: number | null;
  teamName?: string | null;
  avatarUrl?: string;
  createdAt: string;
  updatedAt: string;
};

export type TaskStatus = "ASSIGNED" | "STARTED" | "ONGOING" | "COMPLETED" | "PASSED_DEADLINE";

export type UserRole = "ADMIN" | "USER";

export type Task = {
  id: number;
  taskName: string;
  taskDescription: string;
  status: TaskStatus;
  dueDate: string;
  createdAt: string;
  updatedAt: string;
  userId: number;
  userName: string;
};

export type Team = {
  id: number;
  name: string;
  imageUrl?: string;
};
