import { useState, useEffect, useCallback } from 'react';
import ItemList from './components/ItemList';
import ItemForm from './components/ItemForm';
import { getItems, createItem, updateItem, deleteItem } from './services/api';
import './styles/App.css';

function App() {
  const [items, setItems] = useState([]);
  const [editingItem, setEditingItem] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchItems = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getItems();
      setItems(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchItems();
  }, [fetchItems]);

  const handleCreateOrUpdate = async (item) => {
    setLoading(true);
    setError(null);
    try {
      if (editingItem) {
        await updateItem(editingItem.id, item);
      } else {
        await createItem(item);
      }
      fetchItems();
      setEditingItem(null);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (item) => {
    setEditingItem(item);
  };

  const handleDelete = async (id) => {
    setLoading(true);
    setError(null);
    try {
      if (window.confirm('Are you sure you want to delete this item?')) {
        await deleteItem(id);
        fetchItems();
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app-container">
      <h1>CRUD App</h1>
      <ItemForm onSubmit={handleCreateOrUpdate} item={editingItem} />
      {loading && <p>Loading...</p>}
      {error && <p>Error: {error}</p>}
      {!loading && !error && (
        <ItemList items={items} onEdit={handleEdit} onDelete={handleDelete} />
      )}
    </div>
  );
}

export default App;