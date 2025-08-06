// server.js
require('dotenv').config();
const express    = require('express');
const mysql      = require('mysql2/promise');
const bodyParser = require('body-parser');
const cors       = require('cors');
const path       = require('path');

const app = express();
app.use(cors());
app.use(bodyParser.json());
// 정적 파일 제공 (HTML/CSS/JS)
app.use(express.static(path.join(__dirname, 'public')));

const pool = mysql.createPool({
  host:     process.env.DB_HOST,
  port:     process.env.DB_PORT,
  user:     process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_NAME,
  waitForConnections: true,
  connectionLimit:   10,
});

// 전체 그룹 조회
app.get('/api/groups', async (req, res) => {
  const [rows] = await pool.query('SELECT * FROM groups');
  res.json(rows);
});

// 그룹 생성
app.post('/api/groups', async (req, res) => {
  const { name, category, privacy, maxMembers, destination, ownerId, storeId } = req.body;
  try {
    const [result] = await pool.execute(
      `INSERT INTO groups
      (name, category, privacy, max_members, destination, owner_id, store_id,created_ts)
      VALUES (?, ?, ?, ?, ?, ?, ?,?)`,
      [name, category, privacy, maxMembers, destination, ownerId, storeId, new Date() ]
    );
    res.json({ success: true, groupId: result.insertId });
  } catch (err) {
    console.error(err.sqlMessage || err.message);
    res.status(500).json({ success: false, error: err.message });
  }
});

// 그룹 삭제
app.delete('/api/groups/:id', async (req, res) => {
  try {
    await pool.execute('DELETE FROM groups WHERE group_id = ?', [req.params.id]);
    res.json({ success: true });
  } catch (err) {
    console.error(err);
    res.status(500).json({ success: false, error: err.message });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`✔ Server running at https://localhost:${PORT}`);
});
