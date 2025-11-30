import { useState, useEffect } from 'react';
import ItemList from './components/ItemList';
import ItemForm from './components/ItemForm';
import { getItems, createItem, updateItem, deleteItem } from './services/api';

function App() {
  const [items, setItems] = useState([]);
  const [editingItem, setEditingItem] = useState(null);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    const data = await getItems();
    setItems(data);
  };

  const handleCreateOrUpdate = async (item) => {
    if (editingItem) {
      await updateItem(editingItem.id, item);
    } else {
      await createItem(item);
    }
    fetchItems();
    setEditingItem(null);
  };

  const handleEdit = (item) => {
    setEditingItem(item);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this item?')) {
      await deleteItem(id);
      fetchItems();
    }
  };

  return (
    <div>
      <h1>CRUD App</h1>
      <ItemForm onSubmit={handleCreateOrUpdate} item={editingItem} />
      <ItemList items={items} onEdit={handleEdit} onDelete={handleDelete} />
    </div>
  );
}

export default App;