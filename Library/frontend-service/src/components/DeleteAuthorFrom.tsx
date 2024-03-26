import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const DeleteAuthorForm: React.FC = () => {
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
    authorname: '',
    biography: '',
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
      const url = `http://localhost:8080/storageservice/api/authors/deleteAuthor?id=${formData.id}&authorname=${encodeURIComponent(formData.authorname)}&biography=${encodeURIComponent(formData.biography)}`;
      await axios.delete(url, {
        withCredentials: true
      });
      console.log('Автор удален');
    } catch (error) {
      setError('Произошла ошибка при удалении автора.');
      console.log(error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      {error && <div>{error}</div>}
      <input type="text" name="id" placeholder="ID" onChange={handleChange} />
      <input type="text" name="authorname" placeholder="Author Name" onChange={handleChange} />
      <input type="text" name="biography" placeholder="Biography" onChange={handleChange} />
      <button type="submit">Delete Author</button>
    </form>
  );
};

export default DeleteAuthorForm;
