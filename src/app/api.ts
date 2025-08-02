import axios from "axios";

const API_BASE_URL = "https://chic-integrity-production.up.railway.app/api";
export const fetchTeams = async () => {
  const response = await axios.get(`${API_BASE_URL}/teams`);
  return response.data;
};

export const fetchUsers = async () => {
  const response = await axios.get(`${API_BASE_URL}/users`);
  return response.data;
};

export const registerUsers = async (
  username: string,
  email: string,
  password: string
) => {
  const response = await axios.post(`${API_BASE_URL}/auth/register`, {
    username,
    email,
    password,
    role: "USER" // hardcoded role
  });
  return response.data;
};

export const login = async (
  email: string,
  password: string
) => {
  const response = await axios.post(`${API_BASE_URL}/auth/login`, {
    email,
    password,
  });
  return response.data;
};

export const fetchTasks = async () => {
const response = await axios.get(`${API_BASE_URL}/tasks`);
return response.data;
};


export const fetchCurrentUser = async () => {
  const token = localStorage.getItem("token"); // get token from localStorage
  const response = await axios.get(`${API_BASE_URL}/users/me`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

export const fetchAllUsers = async () => {
  const token = localStorage.getItem("token"); // Assuming this is a protected route
  const response = await axios.get(`${API_BASE_URL}/users`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};
