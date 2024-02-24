import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

interface Book {
  id: number;
  title: string;
  authorName: string;
  description: string;
  genre: string;
  publisher: string;
  year_published: number;
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
    <div>
      <h1>{book.title}</h1>
      <p>Автор: {book.authorName}</p>
      <p>Описание: {book.description}</p>
      <p>Жанр: {book.genre}</p>
      <p>Издатель: {book.publisher}</p>
      <p>Год публикации: {book.year_published}</p>

      <a href={`http://localhost:8080/catalogservice/api/books/download/${id}`} target="_blank" rel="noopener noreferrer">
        <button disabled={loading}>
          {loading ? 'Загрузка...' : 'Скачать книгу'}
        </button>
      </a>
    </div>
  );
};

export default BookDetails;