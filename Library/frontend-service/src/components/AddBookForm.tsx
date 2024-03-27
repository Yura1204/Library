import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styles from './Form.module.css';

const SimpleForm: React.FC = () => {
  const [formData, setFormData] = useState<{ [key: string]: string | number | File }>({});
  const [error, setError] = useState<string | null>(null);
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

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files && e.target.files[0];
    if (file) {
      setFormData({ ...formData, book_content: file });
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null); // Сброс ошибки перед отправкой запроса
    try {
      const postData = new FormData();
      for (const key in formData) {
        if (key === 'book_content') {
          postData.append(key, formData[key] as File);
        } else {
          postData.append(key, formData[key] as string);
        }
      }
      const response = await axios.post('http://localhost:8080/storageservice/api/books/processBookInput', postData, {
        withCredentials: true
      });
      console.log(response.data);
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 401) {
        setError('Пользователь не авторизован. Пожалуйста, войдите в систему.');
      } else {
        setError('Произошла ошибка при отправке формы.');
      }
    }
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      {error && <div>{error}</div>}
      <input type="text" name="title" placeholder="Title" onChange={handleChange} />
      <input type="text" name="authorname" placeholder="Author Name" onChange={handleChange} />
      <input type="text" name="description" placeholder="Description" onChange={handleChange} />
      <input type="text" name="genre" placeholder="Genre" onChange={handleChange} />
      <input type="text" name="publisher" placeholder="Publisher" onChange={handleChange} />
      <input type="number" name="year_published" placeholder="Year Published" onChange={handleChange} />
      <input type="file" name="book_content" onChange={handleFileChange} />
      <button type="submit">Submit</button>
    </form>
  );
};

export default SimpleForm;