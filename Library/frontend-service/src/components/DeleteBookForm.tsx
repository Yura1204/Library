import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const DeleteBookForm: React.FC = () => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const checkAuthStatus = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/auth/status', {
          withCredentials: true
        });
        setIsAuthenticated(response.data.authenticated);
      } catch (error) {
        setIsAuthenticated(false);
      }
    };
    checkAuthStatus();
  }, []);

  useEffect(() => {
    if (isAuthenticated === false) {
      window.location.href = 'http://localhost:8080/login'; // Перенаправление на страницу входа
    }
  }, [isAuthenticated, navigate]);
  const [formData, setFormData] = useState({
    id: '',
    title: '',
    authorname: '',
    description: '',
    genre: '',
    publisher: '',
    year_published: 0,
  });
  const [error, setError] = useState<string | null>(null);
  

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null); // Сброс ошибки перед отправкой запроса
    try {
      const url = `http://localhost:8080/storageservice/api/books/deleteBook?id=${formData.id}&title=${encodeURIComponent(formData.title)}&authorname=${encodeURIComponent(formData.authorname)}&description=${encodeURIComponent(formData.description)}&genre=${encodeURIComponent(formData.genre)}&publisher=${encodeURIComponent(formData.publisher)}&year_published=${formData.year_published}`;
      await axios.delete(url, {
        withCredentials: true
      });
      console.log('Книга удалена');
    } catch (error) {
      setError('Произошла ошибка при удалении книги.');
      console.log(error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      {error && <div>{error}</div>}
      <input type="text" name="id" placeholder="ID" onChange={handleChange} />
      <input type="text" name="title" placeholder="Title" onChange={handleChange} />
      <input type="text" name="authorname" placeholder="Author Name" onChange={handleChange} />
      <input type="text" name="description" placeholder="Description" onChange={handleChange} />
      <input type="text" name="genre" placeholder="Genre" onChange={handleChange} />
      <input type="text" name="publisher" placeholder="Publisher" onChange={handleChange} />
      <input type="number" name="year_published" placeholder="Year Published" onChange={handleChange} />
      <button type="submit">Delete Book</button>
    </form>
  );
};

export default DeleteBookForm;
