import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, Link } from 'react-router-dom';
import styles from './BookDetails.module.css';

interface Book {
  id: number;
  title: string;
  authorName: string;
  description: string;
  genre: string;
  publisher: string;
  year_published: number;
  author_id: number;
}

const BookDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>(); // Получаем id из URL
  const [book, setBook] = useState<Book | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      const response = await axios.get<Book>(`http://localhost:8080/catalogservice/api/books/book/${id}`);
      setBook(response.data);
    };

    fetchData();
  }, [id]); // Запускаем эффект при изменении id

  if (!book) {
    return <div>Загрузка...</div>;
  }

  return (
    <div className={styles.bookDetails}>
      <h1 className={styles.bookTitle}>{book.title}</h1>
      <p className={styles.bookInfo}>
        Автор: <Link to={`/author/${book.author_id}`}>{book.authorName}</Link>
      </p>
      <p className={styles.bookInfo}>Описание: {book.description}</p>
      <p className={styles.bookInfo}>Жанр: {book.genre}</p>
      <p className={styles.bookInfo}>Издатель: {book.publisher}</p>
      <p className={styles.bookInfo}>Год публикации: {book.year_published}</p>
  
      <a
        href={`http://localhost:8080/catalogservice/api/books/download/${id}`}
        target="_blank"
        rel="noopener noreferrer"
      >
        <button className={styles.downloadButton} disabled={loading}>
          {loading ? 'Загрузка...' : 'Скачать книгу'}
        </button>
      </a>
    </div>
  );
};

export default BookDetails;