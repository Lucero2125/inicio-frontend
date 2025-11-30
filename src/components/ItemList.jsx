function ItemList({ items, onEdit, onDelete }) {
  return (
    <ul>
      {items.map((item) => (
        <li key={item.id}>
          {item.name}: {item.description}
          <button onClick={() => onEdit(item)}>Edit</button>
          <button onClick={() => onDelete(item.id)}>Delete</button>
        </li>
      ))}
    </ul>
  );
}

export default ItemList;