<template>
  <el-container>
      <el-form :model="form" ref="form"  :rules="rules">
            <el-form-item prop="movieName" label="电影名称" >
                <el-input v-model="form.movieName"></el-input>
            </el-form-item>
            <el-form-item prop="moviePath" label="电影路径">
               <el-input v-model="form.moviePath"></el-input>
            </el-form-item>
            <el-form-item prop="posterPath" label="封面路径">
               <el-input v-model="form.posterPath"></el-input>
            </el-form-item>
            <el-form-item prop="movieDescription" label="电影描述">
                <el-input v-model="form.movieDescription" placeholder="示例1080P.中英双字" style="font-size:12px"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button plain type="primary" @click="submit">提交</el-button>
                <el-button plain type="info" @click="resetFields">重置</el-button>
            </el-form-item>   
      </el-form>
  </el-container>
</template>

<script>
import config from '../utils/config'
import axios from 'axios'
import { Message } from 'element-ui'
import util from '../utils/util'
export default {
    data(){
        return {
            form:{
                movieName:"",
                moviePath:"",
                posterPath:"",
                movieDescription:""
            },
            rules:{
                movieName:[
                    {required:true,message:"电影名不能为空",trigger:"blur"},
                    { min:1,max:100,message:"长度要在1到100之间",trigger:"blur"}
                ],
                moviePath:[
                    { required:true, message:"路径不能为空",trigger:"blur"},
                    { min:1,max:250, message:"长度要在1到250之间", trigger:"blur"}
                ],
                posterPath:[
                    { required:true, message:"路径不能为空", trigger:"blur"},
                    {  min:1,max:250, message:"长度要在1到250之间", trigger:"blur"}
                ],
                movieDescription:[
                    { required:true, message:"描述不能为空", trigger:"blur"},
                    { min:1, max:100, message:"长度要在1到100之间", trigger:"blur"}
                ]
            }
        }
    },
    methods:{
       submit(){
           let addVideoCom = this;          
           this.$refs.form.validate( (valid) => {
               if(valid){
                   axios.put(config.baseURL+"/video/movie",
                   {
                       movieName: addVideoCom.form.movieName,
                       moviePath: addVideoCom.moviePath,
                       movieDescription: addVideoCom.form.movieDescription,
                       posterPath: addVideoCom.posterPath 
                   }
                   )
                   .then( response => {
                       let resp = response.data;
                       if(resp.status == 200){
                           Message({
                               type:"success",
                               message: '上传成功',
                               showClose: true
                           });
                          addVideoCom.resetFields();
                       }
                       else {
                           return Promise.reject(resp.message);
                       }
                   })
                   .catch(err => {
                       Message({
                           type:"error",
                           message:err,
                           showClose:true
                       });
                   });
               }
               else{
                     Message({
                       type:"error",
                       message:"请完善数据后提交",
                       showClose:true
                   })
                   return false;
               }
           })
       },
       resetFields(){
            this.$refs.form.resetFields();
       }
    },
    computed:{
        moviePath(){
            let path = this.form.moviePath;
            return util.trimQuote(path);
        },
        posterPath(){
            let path = this.form.posterPath;
            return util.trimQuote(path);
        }
    }
}
</script>

<style scoped>


</style>