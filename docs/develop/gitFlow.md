# Git使用规范

## 1. 说明
- master分支上存放的应该是随时可供在生产环境中部署的代码
- develop分支是保存当前最新开发成果的分支
- \*-develop为个人分支，每个人只能在各自的分支上独立开发。而不应该使用其他分支进行代码提交。命名格式：名字首字母-develop，如“吴嘉华”同学的分支名称为 ```wjh-develop```

## 2. 功能开发流程
1. 先基于develop分支在本地开辟一个新的分支，在这个本地的分支进行开发

2. 把本地分支push到远端，发起Review Issue，组员们通过讨论后若通过，则将该分支合并到develop分支上，否则继续改进

## 3. 命令参考
1. 克隆仓库到本地
  ```
  git clone git@github.com:jayjiahua/Cinpockema.git
  ```

2. 切换到develop分支
  ```
  git checkout develop
  ```

3. 在develop分支基础上开辟一条新的分支
  ```
  git checkout -b xxx-develop
  ```

4. 推送分支
  ```
  git push origin yourdev:yourdev
  ```

5. 合并分支(假设当前在xxx-develop分支下)
  ```
  git merge --no-ff develop
  ```

6. 更新分支
  ```
  git pull origin develop
  ```

## 4. Example
对于一个从未参与本项目的人，他应该这么做：
1. 克隆仓库
  ```
  git clone git@github.com:jayjiahua/Cinpockema.git
  ```
  
2. 切换到develop分支
  ```
  git checkout develop
  ```

3. 在develop分支基础上开辟一条新的分支
  ```
  git checkout -b xxx-develop
  ```
  
4. 通过操作3，已经自动切换到xxx-develop分支，可在此分支下coding。

5. Coding完成并测试通过后，查看分支状态
  ```
  git status
  ```
  
6. 会出现一个列表，红色的是待提交的文件。根据列表上的条目，把自己需要提交的文件放到待提交区（filename可以用tab键补全）
  ```
  git add filename
  ```
  
7. 添加完成后，提交（必须有-m参数，即更新描述）
  ```
  git commit -m "add sth"
  ```
  
8. 然后push上去远端
  ```
  git push origin xxx-develop:xxx-develop
  ```
  
9. 请求组员review，继续改进代码
