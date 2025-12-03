const API_URL = import.meta.env.VITE_API_BASE_URL;
const RESOURCE_PATH = '/items';

export const getItems = async () => {
  const response = await fetch(`${API_URL}${RESOURCE_PATH}`);
  return await response.json();
};

export const createItem = async (item) => {
  const response = await fetch(`${API_URL}${RESOURCE_PATH}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(item),
  });
  return await response.json();
};

export const updateItem = async (id, item) => {
  const response = await fetch(`${API_URL}${RESOURCE_PATH}/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(item),
  });
  return await response.json();
};

export const deleteItem = async (id) => {
  await fetch(`${API_URL}${RESOURCE_PATH}/${id}`, {
    method: 'DELETE',
  });
};