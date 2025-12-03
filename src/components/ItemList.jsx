function ItemList({ items, onEdit, onDelete }) {
  return (
    <ul className="item-list">
      {items.map((item) => (
        <li key={item.id} className="item">
          <span>{item.name}: {item.description}</span>
          <div>
            <button onClick={() => onEdit(item)}>Edit</button>
            <button onClick={() => onDelete(item.id)}>Delete</button>
          </div>
        </li>
      ))}
    </ul>
  );
}

export default ItemList;