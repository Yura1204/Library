import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import styles from './BookList.module.css';

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
    <div className={styles.bookList}>
      {books.map(book => (
        <div className={styles.bookItem} key={book.id}>
          <Link to={`/books/${book.id}`}>
            <h2 className={styles.bookTitle}>{book.title}</h2>
            <p className={styles.bookAuthor}>{book.authorName}</p>
          </Link>
        </div>
      ))}
    </div>
  );
};

export default BookList;