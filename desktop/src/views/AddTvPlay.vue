<template>
  <el-container>
      <el-form :model="season" :rules="rules" ref="season">
          
          <el-form-item prop="name" label="电视剧名称">
              <el-input v-model="season.name"></el-input>
          </el-form-item>
          
          <el-form-item prop="index" label="季数序号">
              <el-select v-model="season.index" placeholder="选择要上传第几季">
                  <el-option v-for="number in 10" :value="number" :key="number"></el-option>
              </el-select>
          </el-form-item>

          <el-form-item prop="cover" label="封面路径">
              <el-input v-model="season.cover"></el-input>
          </el-form-item> 

          <el-form-item prop="episodeNumber" label="集数">
              <el-input v-model="season.episodeNumber"></el-input>
          </el-form-item>

          <el-form-item prop="rootDir" label="视频根目录">
              <el-input v-model="season.rootDir"></el-input>
          </el-form-item>

          <el-form-item prop="expression" label="文件名表达式">
              <el-input placeholder="表达式语法参考java String.format语法" v-model="season.expression"></el-input>
          </el-form-item>

          <el-form-item>
              <el-button plain type="primary" @click="submit">提交</el-button>
              <el-button plain type="info" @click="reset">重置</el-button>
          </el-form-item>

      </el-form>
  </el-container>
</template>

<script>
import axios from 'axios'
import config from '../utils/config'
import { Message } from 'element-ui'
import util from '../utils/util'
export default {
    data(){
        return {
            season:{
                name:"",
                index:"",
                cover:"",
                episodeNumber:"",
                rootDir:"",
                expression:""
            },
            rules:{
                name:[
                    { required:true, message:"电视剧名称不能为空", trigger:"blur" },
                    { min:1, max:100, message:"长度要在1到100之间", trigger:"blur" }
                ],
                index:[
                    { required:true, message:"季数不能为空",trigger:"blur" }
                ],
                cover:[
                    { required:true,message:"封面路径不能为空", trigger:"blur"},
                    { min:1, max:255,message:"长度要在1到255之间", trigger:"blur" }
                ],
                episodeNumber:[
                    { required:true, message:"集数不能为空", trigger:"blur"},
                    { min:1, max:2, message:"长度要在1到2之间", trigger:"blur"}
                ],
                rootDir:[
                    { required:true, message:"路径不能为空", trigger:"blur"},
                    { min:1, max:230, message:"长度要在1到230之间", trigger:"blur"}
                ],
                expression:[
                    { required:true,message:"表达式不能为空", trigger:"blur"},
                    { min:1, max:250,message:"长度要在1到250之间", trigger:"blur"}
                ]
            }
        }
    },
    computed:{
        cover(){
            return util.trimQuote(this.season.cover);
        },
        rootDir(){
            return util.trimQuote(this.season.rootDir);
        }
    },
    methods:{
        submit(){
            let addTvPlayCom = this;
            this.$refs.season.validate( valid => {
                if(valid){
                    axios.put(config.baseURL+"/video/season",{
                        name:addTvPlayCom.season.name,
                        index: addTvPlayCom.season.index,
                        cover: addTvPlayCom.cover,
                        rootDir: addTvPlayCom.rootDir,
                        episodeNumber: addTvPlayCom.season.episodeNumber,
                        expression: addTvPlayCom.season.expression
                    })
                         .then( response => {
                             let resp = response.data;
                             console.log(response);
                             if(resp.status == 200){
                                 Message({
                                     type:"success",
                                     message:"上传成功！",
                                     showClose:true
                                 });
                                 addTvPlayCom.reset();
                             }
                             else{
                                 return Promise.reject(resp.message);
                             }
                         })
                         .catch( err => Message({
                             type:"error",
                             message:err,
                             showClose:true
                         }) );
                }
                else{
                    Message({
                        type:"error",
                        message:"请完善信息后提交!",
                        showClose:true
                    });
                    return false;
                }
            })
        },
        reset(){
            this.$refs.season.resetFields();
        }
    }
}
</script>

<style>

</style>