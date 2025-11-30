import { useState, useEffect } from 'react';

function ItemForm({ onSubmit, item }) {
  const [formData, setFormData] = useState({ name: '', description: '' });

  useEffect(() => {
    if (item) {
      setFormData({ name: item.name, description: item.description });
    } else {
      setFormData({ name: '', description: '' });
    }
  }, [item]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
    setFormData({ name: '', description: '' });
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        name="name"
        value={formData.name}
        onChange={handleChange}
        placeholder="Name"
        required
      />
      <input
        type="text"
        name="description"
        value={formData.description}
        onChange={handleChange}
        placeholder="Description"
        required
      />
      <button type="submit">{item ? 'Update' : 'Create'}</button>
    </form>
  );
}

export default ItemForm;