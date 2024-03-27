import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styles from './Form.module.css';

const AuthorForm: React.FC = () => {
  const [formData, setFormData] = useState<{ [key: string]: string }>({});
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

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null); // Сброс ошибки перед отправкой запроса
    try {
      const postData = new FormData();
      for (const key in formData) {
        postData.append(key, formData[key]);
      }
      const response = await axios.post('http://localhost:8080/storageservice/api/authors/processAuthorInput', postData, {
        withCredentials: true
      });
      console.log(response.data);
    } catch (error) {
      setError('Произошла ошибка при отправке формы.');
    }
  };
  

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      {error && <div>{error}</div>}
      <input type="text" name="authorname" placeholder="Author Name" onChange={handleChange} />
      <input type="text" name="biography" placeholder="Biography" onChange={handleChange} />
      <button type="submit">Submit</button>
    </form>
  );
};

export default AuthorForm;