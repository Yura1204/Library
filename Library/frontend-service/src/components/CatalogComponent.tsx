import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

interface Book {
  id: number;
  title: string;
  authorName: string;
  // ... другие поля книги
}

const BookList: React.FC = () => {
  const [books, setBooks] = useState<Book[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await axios.get<Book[]>('http://localhost:8080/catalogservice/api/books');
      setBooks(response.data);
    };

    fetchData();
  }, []);

  return (
    <div>
      {books.map(book => (
        <div key={book.id}>
          <Link to={`/books/${book.id}`}>
            <h2>{book.title}</h2>
            <p>{book.authorName}</p>
          </Link>
        </div>
      ))}
    </div>
  );
};

export default BookList;