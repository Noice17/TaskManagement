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

export const fetchTasks = async () => {
  const response = await axios.get(`${API_BASE_URL}/tasks`);
  return response.data;
};